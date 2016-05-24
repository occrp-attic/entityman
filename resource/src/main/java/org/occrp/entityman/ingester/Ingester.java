package org.occrp.entityman.ingester;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Ingester implements Runnable {

	// TODO thread pool of workers, 
	// should be started on application context load
	
	volatile protected boolean quitRequested = false;
	
	protected Logger log = LogManager.getLogger(getClass().getName());

	@Value("${folder.input:/opt/entityman/input}")
	private String folder_input;
	@Value("${folder.complete:/opt/entityman/complete}")
	private String folder_complete;
	@Value("${folder.error:/opt/entityman/error}")
	private String folder_error;
	@Value("${folder.tmp:/opt/entityman/tmp}")
	private String folder_tmp;

	private void checkFolder(File folder) {
		log.info("Validating folder : {}",folder);
		while (!folder.exists()) {
			log.info("Creating Folder : {}",folder);
			try {
				FileUtils.forceMkdir(folder);
			} catch (IOException e) {
				log.error("Failed to create folder : {}",folder,e);
			}
		}
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public void run() {

		File folderInput = new File(folder_input);
		File folderComplete = new File(folder_complete);

		for (File f : new File[]{folderInput,folderComplete}) {
			checkFolder(f);
		}
		
		while (!quitRequested) {
			log.debug(".");

			Collection<File> files = FileUtils.listFiles(folderInput, new IOFileFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return true;
				}
				@Override
				public boolean accept(File file) {
					return FileUtils.isFileOlder(file, System.currentTimeMillis() - 10000);
				}
			}, new IOFileFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return true;
				}
				@Override
				public boolean accept(File file) {
					return FileUtils.isFileOlder(file, System.currentTimeMillis() - 10000);
				}
			});
			
			if (files.size()>0) {
				for (File f : files) {
					// TODO send files into the blocking queue to be processed by workers
					// the file will be moved from worker thread
					try {
						FileUtils.moveFile(f, 
							new File(folderComplete, 
								f.getName()+"."+sdf.format(new Date())));
					} catch (IOException e) {
						log.error(e);
					}
				}
			} else {
				// TODO sleep
			}
		}
		
	}
}
