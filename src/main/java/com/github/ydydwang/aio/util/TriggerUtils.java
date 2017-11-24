package com.github.ydydwang.aio.util;

import com.github.ydydwang.aio.channel.ChannelContext;
import com.github.ydydwang.aio.channel.ChannelInboundHandler;
import com.github.ydydwang.aio.collection.ListNode;

public class TriggerUtils {

	@SuppressWarnings("rawtypes")
	public static void channelActive(ListNode<ChannelInboundHandler> listNode
			, ChannelContext channelContext) {
		while (listNode != null) {
			try {
				listNode.getVal()
						.channelActive(channelContext);
			} catch (Exception e) {
			}
			listNode = listNode.getNext();
		}
	}

	@SuppressWarnings("rawtypes")
	public static void channelInactive(ListNode<ChannelInboundHandler> listNode
			, ChannelContext channelContext) {
		while (listNode != null) {
			try {
				listNode.getVal()
						.channelInactive(channelContext);
			} catch (Exception e) {
			}
			listNode = listNode.getNext();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void channelRead(ListNode<ChannelInboundHandler> listNode
			, ChannelContext channelContext, Object msg) {
		while (listNode != null) {
			try {
				msg = listNode.getVal()
						.channelRead(channelContext, msg);
			} catch (Exception e) {
				TriggerUtils.readFailed(channelContext.getHandlerList(), channelContext, e.getCause());
				break;
			}
			listNode = listNode.getNext();
		}
	}

	@SuppressWarnings("rawtypes")
	public static void readFailed(ListNode<ChannelInboundHandler> listNode
			, ChannelContext channelContext, Throwable cause) {
		while (listNode != null) {
			try {
				listNode.getVal()
						.readFailed(channelContext, cause);
			} catch (Exception e) {
			}
			listNode = listNode.getNext();
		}
	}

	@SuppressWarnings("rawtypes")
	public static void acceptFailed(ListNode<ChannelInboundHandler> listNode
			, Throwable cause) {
		while (listNode != null) {
			try {
				listNode.getVal()
						.acceptFailed(cause);
			} catch (Exception e) {
			}
			listNode = listNode.getNext();
		}
	}
}
