package goahead.libs.slick

import java.security.{PrivateKey, PublicKey}

import goahead.libs.model.Magic._

import scala.reflect.ClassTag

/**
 * 隐式转换
 */
trait Implicits { self: SlickTrait =>
  import profile.api._

  // 原有类型  =》数据库类型的转换
  implicit val PrivateKeyMapper =
    MappedColumnType.base[PrivateKey, String](x => x.getEncoded.base64, x => x.base64.privateKey)
  implicit val PublicKeyMapper =
    MappedColumnType.base[PublicKey, String](x => x.getEncoded.base64, x => x.base64.publicKey)
  // 用于强类型 ID 映射成 Slick 的字段。为了提高运行和编译性能, 需要为每个 ID 显式指定 Mapper(采用泛型方法获取 Mapper 存在严重的性能隐患)
  def IDMapper[T, U: BaseColumnType](f1: U => T, f2: T => Option[U])(implicit c: ClassTag[T]): BaseColumnType[T] = {
    MappedColumnType.base[T, U](to => f2(to).get, from => f1(from))
  }
}
