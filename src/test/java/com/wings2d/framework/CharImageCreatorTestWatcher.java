package com.wings2d.framework;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.wings2d.framework.CharImageCreatorTest.ImgWithInfo;

public class CharImageCreatorTestWatcher implements TestWatcher{
	@Override
	public void testFailed(ExtensionContext context, Throwable cause)
	{
		CharImageCreatorTest testInstance = (CharImageCreatorTest)context.getRequiredTestInstance();
		ImgWithInfo imgInfo = getLoggedImg(testInstance, context);
		testInstance.getErrorImages().add(imgInfo);
	}
	
	@Override
	public void testSuccessful(ExtensionContext context)
	{
		CharImageCreatorTest testInstance = (CharImageCreatorTest)context.getRequiredTestInstance();
		ImgWithInfo imgInfo = getLoggedImg(testInstance, context);
		testInstance.getErrorImages().add(imgInfo);
	}
	
	private ImgWithInfo getLoggedImg(final CharImageCreatorTest testInstance, ExtensionContext context)
	{
		String methodName = context.getRequiredTestMethod().getName();
		char testChar = context.getDisplayName().charAt(4);
		ImgWithInfo imgInfo = testInstance.getImgInfoByInfo(methodName, testChar);
		if (imgInfo == null)
		{
			throw new IllegalStateException("Test " + methodName + "(" + testChar + ")" + " has not logged it's generated image!");
		}
		return imgInfo;
	}
}
