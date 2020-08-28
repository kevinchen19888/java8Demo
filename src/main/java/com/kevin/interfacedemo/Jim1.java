package cc.mrbird.defaultinterface;

/**
 * @author kevin chen
 */
public interface Jim1 {

    default void jim() {
        System.out.println("Jim1::jim");
    }
}
