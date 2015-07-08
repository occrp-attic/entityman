package org.occrp.entityman.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Transient;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

public abstract class AObject {

	@Transient
	@JsonIgnore
	protected Logger log = LogManager.getLogger(getClass().getName());
	
	public String toString() {
		final StringBuilder sb = new StringBuilder(500);
		final AObject aobject = this; 
		ReflectionUtils.doWithFields(getClass(), new FieldCallback() {
			@Override
			public void doWith(Field f) throws IllegalArgumentException,
					IllegalAccessException {
				if ((f.getModifiers() & Modifier.PRIVATE) == 0) return; 
				sb.append(sb.length()>0 ? ", " : getClass().getSimpleName()+" [ ");
				f.setAccessible(true);
				try {
					sb.append(f.getName()).append(": ").append(f.get(aobject));
				} catch (Exception e) {
					log.error("toString() failed",e);
				}
			}
		});
		
		return sb.append("]").toString();
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		final AtomicBoolean res = new AtomicBoolean(false);
		if (o instanceof AObject) {
			res.set(true);
			final AObject thisObject = this;
			final AObject thatObject = (AObject)o;
			
			ReflectionUtils.doWithFields(getClass(), new FieldCallback() {
				@Override
				public void doWith(Field f) throws IllegalArgumentException,
						IllegalAccessException {
					if (!res.get()) return; // object are already not equal
					if ((f.getModifiers() & Modifier.STATIC ) > 0) return; // skip static fields
					try {
						f.setAccessible(true);
						Object o1 = f.get(thisObject);
						Object o2 = f.get(thatObject);
						
						res.set(o1 != null ? o1.equals(o2) : o2 == null);
					} catch (Exception e) {
					    log.error(e);
					}
				}
			});
		}
		return res.get();
	}
	
}
