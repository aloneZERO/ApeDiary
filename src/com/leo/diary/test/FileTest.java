package com.leo.diary.test;

import java.io.File;

import org.junit.Test;

public class FileTest {
	@Test
	public void testDelFile() {
		File file = new File("d:/ttt");
		file.deleteOnExit();
		
		// 非空文件夹无法直接删除。
	}
}
