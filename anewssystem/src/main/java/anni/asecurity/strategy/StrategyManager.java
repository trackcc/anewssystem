package anni.asecurity.strategy;


/**
 * 策略管理器.
 *
 * @author Lingo
 * @since 2007-06-17
 */
public class StrategyManager {
    /**
     * 无权限管理.
     */
    public static final int NO_SECURITY = 0;

    /**
     * 简单权限管理.
     */
    public static final int SIMPLE_SECURITY = 1;

    /**
     * springside式权限管理.
     */
    public static final int SPRINGSIDE_SECURITY = 2;
}
