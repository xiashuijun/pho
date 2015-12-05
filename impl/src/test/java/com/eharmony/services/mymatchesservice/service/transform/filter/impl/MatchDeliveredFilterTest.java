package com.eharmony.services.mymatchesservice.service.transform.filter.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.eharmony.services.mymatchesservice.MatchTestUtils;
import com.eharmony.services.mymatchesservice.rest.MatchFeedQueryContext;
import com.eharmony.services.mymatchesservice.rest.MatchFeedQueryContextBuilder;
import com.eharmony.services.mymatchesservice.rest.MatchFeedRequestContext;
import com.eharmony.services.mymatchesservice.store.LegacyMatchDataFeedDto;
import com.eharmony.services.mymatchesservice.store.LegacyMatchDataFeedDtoWrapper;

public class MatchDeliveredFilterTest {

	private MatchFeedRequestContext doMatchDeliveredFilter(String fileName) throws Exception{
		
		// read in the feed...
		LegacyMatchDataFeedDto feed = MatchTestUtils.getTestFeed(fileName);
		
		// build the filter context...
		MatchFeedQueryContext qctx = MatchFeedQueryContextBuilder.newInstance().build(); 
		MatchFeedRequestContext ctx = new MatchFeedRequestContext(qctx);
		LegacyMatchDataFeedDtoWrapper legacyMatchDataFeedDtoWrapper = new LegacyMatchDataFeedDtoWrapper(qctx.getUserId());
        legacyMatchDataFeedDtoWrapper.setLegacyMatchDataFeedDto(feed);
        ctx.setLegacyMatchDataFeedDtoWrapper(legacyMatchDataFeedDtoWrapper);
		
		MatchDeliveredFilter filter = new MatchDeliveredFilter();
		return filter.processMatchFeed(ctx);
	}
	
	@Test
	public void testDelivered() throws Exception{ 
		
		MatchFeedRequestContext ctx = 
				doMatchDeliveredFilter("json/getMatches.json");		
		assertNotNull(ctx);
		assertEquals(1, ctx.getLegacyMatchDataFeedDto().getMatches().size());
		System.out.println("reversed userid:" + Long.reverseBytes(60660462));
	}
	
	@Test
	public void testNoDeliveryDate() throws Exception{ 
		
		MatchFeedRequestContext ctx = 
				doMatchDeliveredFilter("json/getMatches_noDeliveryDate.json");		
		assertNotNull(ctx);
		assertEquals(0, ctx.getLegacyMatchDataFeedDto().getMatches().size());
	}
}
