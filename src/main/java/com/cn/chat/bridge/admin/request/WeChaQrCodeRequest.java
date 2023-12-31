
package com.cn.chat.bridge.admin.request;

import com.cn.chat.bridge.common.request.BaseRequest;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信敏感词拦截模型
 *
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("all")
public class WeChaQrCodeRequest implements BaseRequest {

    private String scene;

//    private String page = "pages/web/authorizationView";

    private Boolean check_path = false;

    private String env_version = "develop";

    private Boolean is_hyaline = true;

}
