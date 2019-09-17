package goahead.supermm2.wiki.models

import goahead.libs.model.{Hidden, SHA}

final case class LoginForm(account: String, password: Hidden)

final case class FileReply(name: String, parent: String, sha: SHA, full: String)