package org.ucollective.taf.utilities.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {
	
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    	System.out.println("MARK " + testMethod.getName());
    	annotation.setDataProvider("elc_adv");
        annotation.setDataProviderClass(DataProvider.class);
    }
}