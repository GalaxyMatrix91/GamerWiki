package goahead.supermm2.wiki.models

import goahead.libs.model._

final case class Admin(
                      id: AdminID,
                      token: Token,
                      roleID: RoleID,       // now only 1 role available
                      account: String,      // login account
                      nonce: String,        // random number
                      salt: Hidden,         // encryption salt
                      password: Hidden,      // password
                      remark: Option[String],
                      state: StateID = StateID.Enable   //state
                      )

final case class Role(
                     id: RoleID,
                     token: Token,
                     adminId: AdminID,
                     name: String,
                     isAdmin: Boolean,
                     remark: Option[String],
                     state: StateID = StateID.Enable
                     )