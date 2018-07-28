package com.ty.study.thread

import java.util.concurrent.{Callable, Executor, Executors, Future}


/**
  * Created by root on 2016/5/11.
  */
object ThreadDemo {

  def main(args: Array[String]) {
    val pool = Executors.newFixedThreadPool(5)
//    for (i <- 1 to 10) {
//      pool.execute(new Runnable {
//        def run(): Unit = {
//          println(Thread.currentThread().getName)
//          Thread.sleep(1000)
//        }
//      })
//    }
    val f: Future[Int] = pool.submit(new Callable[Int] {
      override def call(): Int = {
        Thread.sleep(10000)
        100
      }
    })
    var status = f.isDone
    println(s"task status $status")

    Thread.sleep(15000)

    status =  f.isDone
    println(s"task status $status")

    if (status){
      println(f.get())
    }


  }

}