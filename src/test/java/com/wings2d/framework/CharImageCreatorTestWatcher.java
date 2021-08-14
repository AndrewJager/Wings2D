package com.wings2d.framework;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.wings2d.framework.CharImageCreator.CharImageCreatorTest;
import com.wings2d.framework.CharImageCreator.ImgWithInfo;

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
		System.out.println(context.getDisplayName());
		CharImageCreatorTest testInstance = (CharImageCreatorTest)context.getRequiredTestInstance();
		ImgWithInfo imgInfo = getLoggedImg(testInstance, context);
		testInstance.getErrorImages().add(imgInfo);
	}
	
	private ImgWithInfo getLoggedImg(final CharImageCreatorTest testInstance, ExtensionContext context)
	{
		String methodName = context.getRequiredTestMethod().getName();
		String methodData = context.getDisplayName().substring(6);
		ImgWithInfo imgInfo = testInstance.getImgInfoByInfo(methodName, methodData); 
		
		if (imgInfo == null)
		{
			throw new IllegalStateException("Test " + methodName + "(" + methodData + ")" + " has not logged it's generated image!");
		}
		return imgInfo;
	}
}
