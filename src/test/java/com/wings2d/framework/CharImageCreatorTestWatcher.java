package com.wings2d.framework;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class CharImageCreatorTestWatcher implements TestWatcher{
	@Override
	public void testFailed(ExtensionContext context, Throwable cause)
	{
		CharImageCreatorTest testInstance = (CharImageCreatorTest)context.getRequiredTestInstance();
		checkImgLogged(testInstance.getGeneratedImages(), context.getRequiredTestMethod().getName());
		BufferedImage img = testInstance.getGeneratedImages().get(context.getRequiredTestMethod().getName());
		testInstance.getErrorImages().put(context.getRequiredTestMethod().getName(), img);
	}
	
	@Override
	public void testSuccessful(ExtensionContext context)
	{
		CharImageCreatorTest testInstance = (CharImageCreatorTest)context.getRequiredTestInstance();
		checkImgLogged(testInstance.getGeneratedImages(), context.getRequiredTestMethod().getName());
		BufferedImage img = testInstance.getGeneratedImages().get(context.getRequiredTestMethod().getName());
		testInstance.getErrorImages().put(context.getRequiredTestMethod().getName(), img);
	}
	
	private void checkImgLogged(final Map<String, BufferedImage> generatedImgs, final String methodName)
	{
		if (!generatedImgs.containsKey(methodName))
		{
			throw new IllegalStateException("Test " + methodName + " has not logged it's generated image!");
		}
	}
}
