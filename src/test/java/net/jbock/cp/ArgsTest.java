package net.jbock.cp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArgsTest {

    @Test
    void simpleTest() {
        ArgsParser parser = ArgsParser.init(new String[]{"-f", "/tmp", "-r"});
        Args args = parser.parse();
        assertEquals("/tmp", args.file);
        assertTrue(args.recursive);
    }
}