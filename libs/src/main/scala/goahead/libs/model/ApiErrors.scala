package goahead.libs.model

// 系统级错误 10
sealed trait SystemErrors {
  // 系统错误
  def SystemError = GoAheadException(1000, "System Error")
  // 数据库异常
  def DBError = GoAheadException(1001, "Database Error")
  // 资源没有找到
  def NotFound = GoAheadException(1004, "Resource Not Found")
  // 数据不对
  def WrongData = GoAheadException(1005, "Wrong Data")
  // 禁止
  def Forbidden = GoAheadException(1006, "Access Forbidden")
  // 无法删除，要填入无法删除的信息
  def NotRemoved = GoAheadException(1007, "It Can't Remove")
  // 没有操作的权限
  def NoRight = GoAheadException(1008, "Not Right")
  // 余额不足
  def Insufficient = GoAheadException(1009, "Insufficient Balance")
  // 添加失败：名字相同
  def SameName = GoAheadException(1010, "Same Name")
  // 添加失败: 工号相同
  def SameSN = GoAheadException(1011, "Same SN")
  // 资源已存在
  def ResourceExist = GoAheadException(1012, "Resource Exists")
  // 修改密码失败
  def ChangePasswordFailed = GoAheadException(1013, "Wrong user name or old password")
  // 密码格式不对
  def WrongPasswordFormat = GoAheadException(1014, "Wrong password format")
  // 没有数据
  def NoData = GoAheadException(1015, "No Data")
  // 参数错误
  def WrongParam = GoAheadException(1016, "Wrong Parameter")
  // 添加失败: rfid相同
  def SameRF = GoAheadException(1017, "Same RfID")
  // 网络故障
  def NetError = GoAheadException(1018, "Network Error")
  // 访问超时
  def Timeout = GoAheadException(1019, "Timeout")
  // 删除失败：菜品已添加定价计划，无法删除
  def HasPricing = GoAheadException(1020, "Goods Has Pricing")
  // 添加失败：类型重复
  def SameType = GoAheadException(1021, "Same Type")
  // 删除失败：做法已添加定价计划，无法删除
  def GoodsMethodHasPricing = GoAheadException(1022, "GoodsMethod Has Pricing")
  // 删除失败：规格已被使用，无法删除
  def UnitTypeHasUsing = GoAheadException(1023, "UnitType Has using")
  // 菜品必须至少有一个规格
  def GoodsMustHasUnitType = GoAheadException(1024, "Goods must has unitType")
  // 重复操作
  def RepeatAction = GoAheadException(1025, "Repeat Action")
  // 添加规格失败： 菜品已存在相同规格， 无法再次添加
  def UnitTypeHasExsit = GoAheadException(1026, "UnitType has exist")
  // 帐号已经注册
  def AccountHasExsit = GoAheadException(1027, "account has exist")
  // 验证码错误
  def CodeError = GoAheadException(1028, "code error")
  // 缓存已经失效
  def CacheExpired = GoAheadException(1029, "cache expired")
  // 手机号不一致
  def MobileDiffer = GoAheadException(1030, "mobile differ")
  // 没有登录
  def NoLogin = GoAheadException(1031, "no log in")
  // 已经支付
  def YetCashier = GoAheadException(1032, "yet cashier")
  // 已经过期
  def CashierExpire = GoAheadException(1033, "cashier time expire")
  // 部署成功
  def DeployFailure = GoAheadException(1034, "deploy failure")
  def NotCashier = GoAheadException(1035, "not cashier")
  // 等待登录
  def WaitLogin = GoAheadException(1036, "wait login")
  // 扫码登录失败
  def ScanLoginFailure = GoAheadException(1037, "scan login failure")
  // 品牌已经存在
  def BrandExists = GoAheadException(1038, "brand exists")
  // 门店已经存在
  def ShopExists = GoAheadException(1039, "shop exists")
  // 试用门店需要续费
  def ShopTrailRenewals = GoAheadException(1040, "shop trail renewals")
  // 停止续费
  def ForbidRenewals = GoAheadException(1041, "shop forbid renewals")
  //
  def CodeRepetionWrongInput = GoAheadException(1042, "wrong code repetion input")
  // 不支持的浏览器
  def BrowserNotSupport = GoAheadException(1043, "Browser not support")
  // 不支持
  def NotSupport = GoAheadException(1044, "Not Support")
  // 用户不存在或者密码错误
  def LoginError = GoAheadException(1045, "Login Error")
  // 没有权限
  def NoPermission = GoAheadException(1046, "No Permission")
}

// 请求异常 11
sealed trait RequestErrors {
  def InvalidForm = GoAheadException(1101, "Form Validation Failed")
  // 缺少头部信息
  def MissingHeader = GoAheadException(1102, "Missing API Header")
  // 非法头部
  def InvalidHeader = GoAheadException(1103, "Invalid Header")
  // API 提交的对象校验失败
  def InvalidJson = GoAheadException(1104, "Invalid Json")
  // 不是 JSON 对象
  def NoJson = GoAheadException(1105, "Not A Json From POST Request")
  // 没有找到注册信息
  def PosNotFound = GoAheadException(1106, "Pos Machine Is Not Found")
  // Salt 是空的
  def SaltIsEmpty = GoAheadException(1107, "Salt Is Empty. Please Register.")
  // 不合法的 Token
  def InvalidToken = GoAheadException(1108, "Invalid Token")
  // Json 对象没有指定的数据
  def NotBindingData = GoAheadException(1109, "Json Object Has Not Binding Data")
  // Missing File
  def MissingFile = GoAheadException(1110, "Missing File")
  // Too Big
  def TooBigFile = GoAheadException(1111, "File Is Too Big")
  // UUID is wrong
  def WrongUUID = GoAheadException(1112, "Wrong UUID")
  // App not found
  def AppNotFound = GoAheadException(1113, "App Is Not Found")
  // 付款码格式错误
  def AuthCodeOfPayError = GoAheadException(1114, "Auth Code Error")
}

// 时间异常 12
sealed trait TimeErrors {
  def NotComing = GoAheadException(1201, "Begin Time Is Not Coming")
  def Expired = GoAheadException(1202, "Expired")
  def NotInWeek = GoAheadException(1203, "Wrong Week")
  def ServiceHourOverride = GoAheadException(1204, "Service Hour is Override")
  def NotInServiceHour = GoAheadException(1205, "Not In Service Hour")
}

// Shop 信息: 13
sealed trait ShopErrors {
  def InvalidShop = GoAheadException(1301, "Invalid Shop")
  def QRUsed = GoAheadException(1302, "QRCode was used")
  def QRExpired = GoAheadException(1303, "QRCode was expired")
  def QRNotFound = GoAheadException(1304, "QRCode was not found")
  def TableNotFound = GoAheadException(1305, "Table not found")
  def CannotDeleteMasterPos = GoAheadException(1306, "Cannot Delete Master PosMachine")
}

// Coupon 异常 16
trait CouponErrors {
  // 没有找到 Voucher
  def CouponNotFound = GoAheadException(1601, "Coupon Not Found")
  def CouponUsed = GoAheadException(1602, "Coupon Has Been Used")
  def CouponNoUseHere = GoAheadException(1604, "Coupon No Use Here")
}

// 会员异常 17
trait MemberErrors {
  def LackOfBalance = GoAheadException(1701, "The balance of the member is too less")
  def NoCashCard = GoAheadException(1702, "The card is not a cash card")
  def MobileExists = GoAheadException(1703, "Mobile Exists")
  def EmailExists = GoAheadException(1704, "Email Exists")
  def CardExists = GoAheadException(1705, "Card No. Exists")
  def RFIDExists = GoAheadException(1706, "RFID Exists")
  def VerifyCodeExpired = GoAheadException(1707, "Verify Code is expired")
  def YetVerified = GoAheadException(1708, "It has been verified")
  def WrongVerifyCode = GoAheadException(1709, "Wrong Verify Code")
  def MultiMemberTypes = GoAheadException(1710, "Multi MemberTypes")
  def ProfileUpdated = GoAheadException(1711, "Profile was updated")
  def MemberNotFound = GoAheadException(1712, "Member Not Found")
  def NotAck = GoAheadException(1713, "Not ACK")
  def UseChargeNotFound = GoAheadException(1714, "UseCharge Not Found")
  def MultiMembers = GoAheadException(1715, "Multi Members")
  def YetBoundCard = GoAheadException(1716, "Yet Bound Card")
  def VerifyCodeNotFound = GoAheadException(1717, "Verify Code Not Found")
  def MemberPasswordNotMatch = GoAheadException(1718, "Member Password Not Match")
  def MemberPasswordMissing = GoAheadException(1719, "Member Password Is Missing")
  def EmailOrMobileExists = GoAheadException(1720, "Email Or Mobile Exists")
  def ChargeNotFound = GoAheadException(1721, "Charge Not Found")
  def PromotionNotFound = GoAheadException(1722, "Promotion Not Found")
}

// goods 异常 18
sealed trait GoodsErrors {
  def GoodsHasQuoted = GoAheadException(1801, " The goods has been quoted by set")
}

// Voucher 异常 20
sealed trait VoucherErrors {
  // 没有找到 Voucher
  def VoucherNotFound = GoAheadException(2001, "Voucher Not Found")
  def VoucherUsed = GoAheadException(2002, "Voucher Has Been Used")
  def VoucherNotFoundForNoSN = GoAheadException(2003, "Voucher Not Found for Non-SN")
  def VoucherNoUseHere = GoAheadException(2004, "Voucher No Use Here")
  def VoucherTypeUsed = GoAheadException(2005, "Voucher Type is used")
}

// 收银异常 21
sealed trait CashierErrors {
  def NoBill = GoAheadException(2100, "No Cashier Bill")
  def RepeatCashier = GoAheadException(2101, "Bill has been cashiered")
  def InvalidBillID = GoAheadException(2102, "Invalid Bill ID")
  def TimeoutBill = GoAheadException(2103, "Timeout Bill")
  def PosNotConnected = GoAheadException(2104, "Pos has not been connected")
  def TimeoutPay = GoAheadException(2105, "Timeout PayFinish")
}

// 微信错误, 22
sealed trait WeixinErrors {
  def ShortUrlFailed = GoAheadException(2201, "Failed to short url")
  def BillNotFound = GoAheadException(2202, " Bill not found")
  def NotSupportWeixin = GoAheadException(2203, "Shop does not support weixin pay")
  def OpenIDNotFound = GoAheadException(2204, "Not OpenID")
  def UserNotAuth = GoAheadException(2205, "User Not Auth")
  def MemberTypeNotBind = GoAheadException(2207, "Member Type was not binding with Weixin")
  def MenuNotFound = GoAheadException(2208, "Menu Not Found")
  def AuthNotFound = GoAheadException(2209, "Auth Not Found")
  def RefreshTokenNotFound = GoAheadException(2210, "RefreshToken Not Found")
  def TicketAlreadyInvalid = GoAheadException(2211, "ticket already invalid")
  def AppIdAlreadyExists = GoAheadException(2212, "appId already exists")
  def ShopSubMchIdExists = GoAheadException(2213, "Shop SubMchId Exists")
  def SendUserNotNull = GoAheadException(2214, "send to user not null")
  def MPError = GoAheadException(2215, "MP Error")
  def MPNotFound = GoAheadException(2216, "MP Not Found")
  def MPNotAction = GoAheadException(2217, "MP Not Action")
  def MPMemberExist = GoAheadException(2218, "MP Member Exist")
  def MemberTypeNotBindShop = GoAheadException(2219, "MemberType Not Binding Shop")
  def MiniProgSystemBusy = GoAheadException(2220, "MiniProgram system busy")
  def MiniProgCodeExpired = GoAheadException(2221, "MiniProgram user code expired")
  def MiniProgLoginFrequencyLimit = GoAheadException(2222, "MiniProgram Login Frequency Limit")
}

// agent 异常 23
sealed trait AgentErrors {
  def AgentNotConnected = GoAheadException(2301, "Agent Not Connected")
  def NoMessageToAgent = GoAheadException(2302, "No message sent to agent")
  def NoMessageBody = GoAheadException(2303, "No message body")
  def WxPayError = GoAheadException(2304, "Tenpay Error")
  def WxUserPaying = GoAheadException(2305, "User is paying")
  def AckPayError = GoAheadException(2306, "Ack Pay Error")
  def WxOrderPaid = GoAheadException(2307, "Order Was Paid")
  def WxOrderClosed = GoAheadException(2308, "Order Was Closed")
  def WxTradeRepeated = GoAheadException(2309, "Trade Was Repeated")
  def ShopServiceNotEffected = GoAheadException(2310, "Shop Service Not Effected")
}

// 返回给收银机的异常 24
sealed trait PosErrors {
  def InvalidBindToken = GoAheadException(2401, "Invalid Bind Token")
  def PendingBindPos = GoAheadException(2402, "Pending ACKing Token")
  def MasterNotFound = GoAheadException(2403, "Master Not Found")
  def NotBindingManager = GoAheadException(2404, "Not Binding Manager")
}

// 支付宝错误 25
trait AliErrors {
  def AliNoSign = GoAheadException(2501, "No Sign In Ali Response")
  def AliSignVerifyFailed = GoAheadException(2502, "Ali Sign Not Valid")
  def AliNoResponse = GoAheadException(2503, "No Response In Json")
  def AliConfigError = GoAheadException(2504, "Ali Config Error")
  def AliError = GoAheadException(2505, "Ali Error")
}

// 威富通错误 26
trait SwiftErrors {
  def SwiftError = GoAheadException(2601, "Swift Error")
  def SwiftNotFound = GoAheadException(2602, "Swift Not Found")
}

trait ApiErrors
    extends SystemErrors
    with RequestErrors
    with TimeErrors
    with CouponErrors
    with VoucherErrors
    with ShopErrors
    with CashierErrors
    with MemberErrors
    with GoodsErrors
    with WeixinErrors
    with AgentErrors
    with PosErrors
    with AliErrors
    with SwiftErrors

object ApiErrors extends ApiErrors
