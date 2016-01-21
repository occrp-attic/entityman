package org.occrp.entityman.glutton.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.occrp.entityman.glutton.Gluttony;
import org.occrp.entityman.model.IngestedFile;
import org.occrp.entityman.model.ServiceResult;
import org.occrp.entityman.model.entities.AEntity;
import org.occrp.entityman.model.entities.EntityList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import android.util.Log;

@RestController
//@RequestMapping(value = "/")
public class GluttonyController {

	protected Logger log = LogManager.getLogger(getClass().getName());

	@Autowired
	private Gluttony gluttony;
	
	@Value("${tmp.path:/tmp/gluttonywar/}")
	private String tmpPath;
	
	@RequestMapping(value = "/consume", method = RequestMethod.GET)
	public @ResponseBody ServiceResult<String> consume() {
		return new ServiceResult<String>(ServiceResult.CODE_OK, null, "Post files to this URL");
	}

	
	
	private File saveToTempFile(MultipartFile file) throws IOException {
		File folder = new File(tmpPath);
		if (!folder.exists()) {
			FileUtils.forceMkdir(folder);
		}
		
		File tmpFile = null;

		if (!file.isEmpty()) {
			BufferedOutputStream stream = null;
            try {
    			tmpFile = File.createTempFile("tmp_", file.getOriginalFilename(), folder);
    			byte[] bytes = file.getBytes();
                stream = new BufferedOutputStream(new FileOutputStream(tmpFile));
                stream.write(file.getBytes());
                log.debug("You successfully uploaded {}, size {}", file.getOriginalFilename(), file.getSize());
            } catch (Exception e) {
                log.debug("You failed to upload {}", file.getOriginalFilename(), e);
            } finally {
                if (stream!=null) {
                	stream.close();
                }
			}
        } else {
            log.debug("Failed to upload {} because the file was empty.", file.getOriginalFilename());
        }
		return tmpFile;
	}

	@RequestMapping(value = "/consume", method = RequestMethod.POST)
	public @ResponseBody ServiceResult<EntityList> consume(
			@RequestParam(value = "lang", defaultValue = "en") String lang,
			@RequestParam(value="workspace", defaultValue="default") String workspace,
			@RequestParam("file") MultipartFile file) {

		ServiceResult<EntityList> res = new ServiceResult<>();
		EntityList resEntities = new EntityList();
		try {
			File tmpFile = saveToTempFile(file);

			if (tmpFile == null) {
				log.warn("Failed to eat file : {}", file.getOriginalFilename());
			} else {
				IngestedFile ingestedFile = new IngestedFile();
				ingestedFile.setFile(tmpFile);
				log.info("Eating file : {}, {}", file.getOriginalFilename(), ingestedFile);
				List<AEntity> entities = gluttony.call(ingestedFile);

				if (entities != null && entities.size() > 0) {
					resEntities.addAll(entities);
				}

			}
		} catch (Exception e) {
			log.error("Error consuming file : {}", file.getOriginalFilename(), e);
		}

		res.setC(ServiceResult.CODE_OK);
		res.setO(resEntities);

		return res;
	}

	@RequestMapping(value = "/consumeMultiple", method = RequestMethod.POST)
	public @ResponseBody ServiceResult<EntityList> consumeMultiple(
			@RequestParam(value="lang", defaultValue="en") String lang,
			@RequestParam(value="workspace", defaultValue="default") String workspace,
			@RequestParam("file") MultipartFile[] files) {

		ServiceResult<EntityList> res = new ServiceResult<>();
		if (files != null && files.length > 0) {
			EntityList resEntities = new EntityList();
			for (MultipartFile file : files) {
				try {
					File tmpFile = saveToTempFile(file);
					
					if (tmpFile==null) {
						log.warn("Failed to eat file : {}",file.getOriginalFilename());
						continue;
					}
					
					IngestedFile ingestedFile = new IngestedFile();
					ingestedFile.setWorkspace(workspace);
					ingestedFile.setFile(tmpFile);
					ingestedFile.setOriginalFilename(file.getOriginalFilename());
					
					log.info("Eating file : {}, {}", file.getOriginalFilename(), ingestedFile);
					List<AEntity> entities = gluttony.call(ingestedFile);
					
					if (entities!=null && entities.size()>0) {
						resEntities.addAll(entities);
					}
					
				} catch (Exception e) {
					log.error("Error consuming file : {}", file.getOriginalFilename(), e);
				}
			}
					
			res.setC(ServiceResult.CODE_OK);
			res.setO(resEntities);
		} else {
			res.setC(ServiceResult.CODE_ERROR);
			res.setM("Unable to upload. File is empty.");
		}
		
		return res;
	}
}
