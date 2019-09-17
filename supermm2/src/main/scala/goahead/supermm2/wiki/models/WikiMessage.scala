package goahead.supermm2.wiki.models

import goahead.libs.model.Token

sealed trait WikiMessage

trait LoginMessage extends WikiMessage

trait LoginChildMessage extends LoginMessage {
  def token: Token
}

trait WikiUserMessage extends WikiMessage

trait OssMessage extends WikiMessage