package above.allrun.helpers

final class RunReferenceStorage {

    private static final RunReferenceStorage instance = new RunReferenceStorage()
    static List runStorage

    private RunReferenceStorage() {
        runStorage = []
    }

    synchronized static RunReferenceStorage getInstance() {
        return instance
    }

    synchronized static add(object) {
        runStorage << object
    }

    static def getLast() {
        if (runStorage.isEmpty()) {
            throw new Exception(
                    "RunReferenceGlobalStorage isEmpty but was referenced by thread: \""
                    + Thread.currentThread().getName()
                    + "\". Add a run reference using `RunReferenceGlobalStorage.add(run())` in a main thread.")
        }
        try {
            return runStorage.last()
        } catch(ignored) {
        }
    }
}
