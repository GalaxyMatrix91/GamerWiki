package goahead.libs.model

/**
 * 强类型 ID 分为以下几种类型:
 * Short, Int, Long, Double, String
 */
sealed trait MID[T] extends slick.lifted.MappedTo[T] {
  def value: T
}

// Scala 处理泛型的代码比较繁琐, 这里简化处理
trait ShortMID extends MID[Short]
trait IntMID extends MID[Int]
trait LongMID extends MID[Long]
trait DoubleMID extends MID[Double]
trait StringMID extends MID[String]
