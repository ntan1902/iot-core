package com.iot.server.common.utils;

public class ScriptUtils {
    public static final String MSG = "msg";
    public static final String MSG_TYPE = "msgType";
    public static final String RULE_NODE_FUNCTION_NAME = "ruleNodeFunctionName";

    private static final String JS_WRAPPER_PREFIX_TEMPLATE =
            "function %s(msgStr, msgType) { " +
                    "    var msg = JSON.parse(msgStr); " +
                    "    return JSON.stringify(%s(msg, msgType));" +
                    "    function %s(%s, %s) {";
    private static final String JS_WRAPPER_SUFFIX = "\n}" +
            "\n}";

    // function abc(msgStr, msgType) {
    //    var msg = JSON.parse(msgStr);
    //    return JSON.stringify(ruleNodeFunc(msg, msgType));
    //    function ruleNodeFunc(msg, msgType) {
    //        return 'Incoming message:\n' + JSON.stringify(msg);
    //    }
    // }

    public static String getScript(String functionName, String script, String... args) {
        String msgArg;
        String msgTypeArg;
        if (args != null && args.length == 2) {
            msgArg = args[0];
            msgTypeArg = args[1];
        } else {
            msgArg = MSG;
            msgTypeArg = MSG_TYPE;
        }
        String jsWrapperPrefix = String.format(JS_WRAPPER_PREFIX_TEMPLATE, functionName,
                RULE_NODE_FUNCTION_NAME, RULE_NODE_FUNCTION_NAME, msgArg, msgTypeArg);
        return jsWrapperPrefix + script + JS_WRAPPER_SUFFIX;
    }
}
