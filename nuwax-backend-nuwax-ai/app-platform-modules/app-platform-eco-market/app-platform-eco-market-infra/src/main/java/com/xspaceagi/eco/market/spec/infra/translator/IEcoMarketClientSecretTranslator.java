package com.xspaceagi.eco.market.spec.infra.translator;

import com.xspaceagi.eco.market.domain.model.EcoMarketClientSecretModel;
import com.xspaceagi.eco.market.spec.infra.dao.entity.EcoMarketClientSecret;
import com.xspaceagi.system.infra.dao.ICommonTranslator;

/**
 * 生态市场客户端密钥翻译器
 */
public interface IEcoMarketClientSecretTranslator extends ICommonTranslator<EcoMarketClientSecretModel, EcoMarketClientSecret> {
}
