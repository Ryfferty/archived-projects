package com.xspaceagi.system.spec.annotation;

import lombok.Getter;
import lombok.Setter;

/**
 * 敏感字段权限过滤, 目前自己从权限系统里获取,如有无成本权限,售价权限等
 * <p>后续完善中,看实际情况,会继续增加敏感类型的区分和过滤</p>
 */
@Getter
@Setter
public class SensitiveAuthParam {

    /**
     * 用户密码权限
     */
    private Boolean userPasswordFlag;



    public static SensitiveAuthParam passwordAuthBuild(Boolean userPasswordFlag) {
        SensitiveAuthParam sensitiveAuthParam = new SensitiveAuthParam();
        sensitiveAuthParam.setUserPasswordFlag(userPasswordFlag);
        return sensitiveAuthParam;
    }
}
