package com.github.ydydwang.aio.util;

import java.nio.ByteBuffer;

import com.github.ydydwang.aio.channel.ChannelContext;
import com.github.ydydwang.aio.channel.ChannelInboundHandler;
import com.github.ydydwang.aio.list.ListNode;

public class TriggerUtils {

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

	public static void channelRead(ListNode<ChannelInboundHandler> listNode
			, ChannelContext channelContext, ByteBuffer buffer) {
		while (listNode != null) {
			try {
				listNode.getVal()
						.channelRead(channelContext, buffer);
			} catch (Exception e) {
			}
			listNode = listNode.getNext();
		}
	}

	public static void exceptionCaught(ListNode<ChannelInboundHandler> listNode
			, ChannelContext channelContext, Throwable cause) {
		while (listNode != null) {
			try {
				listNode.getVal()
						.exceptionCaught(channelContext, cause);
			} catch (Exception e) {
			}
			listNode = listNode.getNext();
		}
	}

	public static void channelUnregistered(ListNode<ChannelInboundHandler> listNode
			, Throwable cause) {
		while (listNode != null) {
			try {
				listNode.getVal()
						.channelUnregistered(cause);
			} catch (Exception e) {
			}
			listNode = listNode.getNext();
		}
	}
}
