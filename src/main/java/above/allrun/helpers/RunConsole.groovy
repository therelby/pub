package above.allrun.helpers

class RunConsole extends PrintStream {

    RunConsole(OutputStream out) {
        super(out)
    }

    @Override void print(String s) {
        // ... process output string here ...
        if (s) return

        // pass along to actual console output
        super.print(s)
    }
}
