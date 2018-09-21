package net.jbock.cp;

import net.jbock.CommandLineArguments;
import net.jbock.Description;
import net.jbock.LongName;
import net.jbock.ShortName;

class Args {

    final String file;
    final boolean recursive;

    @CommandLineArguments
    Args(@LongName("path")
         @ShortName("f")
         @Description("The file")
                 String file,
         @Description("Is it recursive?")
         @ShortName("r")
                 boolean recursive) {
        this.file = file;
        this.recursive = recursive;
    }
}
