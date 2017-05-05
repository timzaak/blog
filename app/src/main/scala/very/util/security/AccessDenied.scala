package very.util.security

case class AccessDenied(resource: String, access: Access) extends Exception {
  override def getMessage: String = {
    s"${resource} permission denied"
  }
}
