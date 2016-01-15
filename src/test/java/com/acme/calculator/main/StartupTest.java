package com.acme.calculator.main;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pturcott on 1/12/16.
 */
public class StartupTest {
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void argCountTooSmall() {
        Startup.main(new String [] {});
        String [] stdoutLines = systemOutRule.getLog().split(Startup.NEWLINE);
        String className = Startup.class.getName();
        assertEquals(5, stdoutLines.length);
        assertEquals("Unexpected number of arguments (0)",stdoutLines[0]);
        assertEquals("usage:",stdoutLines[1]);
        assertTrue(stdoutLines[2].contains(className));
        assertEquals("example:",stdoutLines[3]);
        assertTrue(stdoutLines[4].contains(className));
    }

    @Test
    public void argCountTooLarge() {
        Startup.main(new String [] {"add", " (2, 3)"});
        String [] stdoutLines = systemOutRule.getLog().split(Startup.NEWLINE);
        String className = Startup.class.getName();
        assertEquals(5, stdoutLines.length);
        assertEquals("Unexpected number of arguments (2)",stdoutLines[0]);
        assertEquals("usage:",stdoutLines[1]);
        assertTrue(stdoutLines[2].contains(className));
        assertEquals("example:",stdoutLines[3]);
        assertTrue(stdoutLines[4].contains(className));
    }

    @Test
    public void mainPrintsResult() {
        Startup.main(new String [] {"let(abc,99,mult(11,abc))"});
        String [] stdoutLines = systemOutRule.getLog().split(Startup.NEWLINE);
        assertEquals(1, stdoutLines.length);
        assertEquals("1089",stdoutLines[0]);
    }
}
