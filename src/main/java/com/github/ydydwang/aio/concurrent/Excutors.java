package com.github.ydydwang.aio.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Excutors {
	private static ExecutorService boss = Executors.newFixedThreadPool(1);
	private static ExecutorService worker = Executors.newFixedThreadPool(4);

	public static ExecutorService getBoss() {
		return boss;
	}
	public static void setBoss(ExecutorService boss) {
		Excutors.boss = boss;
	}
	public static ExecutorService getWorker() {
		return worker;
	}
	public static void setWorker(ExecutorService worker) {
		Excutors.worker = worker;
	}
}
