package very.util.db.postgre

trait WithID {
  type T
  def id: T
}

trait WithIntID extends WithID {
  type T = Int
}

trait WithLongID extends WithID {
  type T = Long
}

trait WithStringID extends WithID {
  type T = String
}
