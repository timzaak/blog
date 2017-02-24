package com.timzaak.repo

trait Character {
  def id: String
  def name: Option[String]
  def friends: List[String]
  def appearsIn: List[Episode.Value]
}
