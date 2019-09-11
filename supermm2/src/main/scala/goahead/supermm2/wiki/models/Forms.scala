package goahead.supermm2.wiki.models

import goahead.libs.model.Hidden

final case class LoginForm(account: String, password: Hidden)
