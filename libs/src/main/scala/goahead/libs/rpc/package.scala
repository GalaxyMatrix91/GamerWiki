package goahead.libs

import goahead.libs.json._
import goahead.libs.model._
package object rpc {

  implicit private[rpc] val DeveloperIDJson = Jsons.id(DeveloperID.apply, DeveloperID.unapply)
  implicit private[rpc] val RpcActionJson = Jsons.id(RpcAction.apply, RpcAction.unapply)
  implicit private[rpc] val TokenJson = Jsons.id(Token.apply, Token.unapply)
  implicit private[rpc] val TradeIDJson = Jsons.id(TradeID.apply, TradeID.unapply)
  implicit private[rpc] val SNJson = Jsons.id(SN.apply, SN.unapply)
  implicit private[rpc] val QianExceptionFormat = new GoAheadExceptionFormat()
  implicit private[rpc] val RpcHeaderJson = Jsons.format[RpcHeader]
  implicit private[rpc] val RpcMessageJson = Jsons.format[RpcMessage]
  implicit private[rpc] val ReplyJson = Jsons.format[RpcReply]

}
