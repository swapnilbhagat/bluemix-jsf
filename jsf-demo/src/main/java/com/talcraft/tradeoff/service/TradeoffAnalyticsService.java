package com.talcraft.tradeoff.service;

import com.talcraft.tradeoff.domain.Options;
import com.talcraft.tradeoff.domain.Problem;

public interface TradeoffAnalyticsService {

	public String getResolutionsForProblem(Problem problem, Options options);

	public String loadProfile(String profileName);

}
