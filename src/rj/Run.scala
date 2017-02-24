package rj

/**
  * Created by rich on 24/02/17.
  */
object Run {
  implicit class IntTimes (x: Int) {
    def times[A](f: =>A): Unit = {
      def loop(current: Int): Unit =

        if(current >0){
          f
          loop(current - 1)
        }
        loop(x)
    }
  }
}

//object Run {
//  implicit class IntTimes(x: Int) {
//    def times [A](f: =>A): Unit = {
//      def loop(current: Int): Unit =
//
//        if(current > 0){
//          f
//          loop(current - 1)
//        }
//      loop(x)
//    }
//  }
//}