package scala

import part1.{MailSystem, Message, MemoryMailStore}

object MailSys extends scala.App {
  val mailStore = new MemoryMailStore();
  val mailSystem = new MailSystem(mailStore);

  val root = new Domain("")
  val cat = new Domain("cat")
  val urv = new Domain("urv")
  val etse = new Domain("etse")
  val estudiants = new Domain("estudiants")
  val user1 = new Account("user1")
  val user2 = new Account("user2")
  val user3 = new Account("user3")
  val user4 = new Account("user4")

  root.addChild(cat)
  cat.addChild(urv)
  urv.addChild(etse)
  urv.addChild(estudiants)
  cat.addChild(user1)
  urv.addChild(user2)
  etse.addChild(user3)
  estudiants.addChild(user4)

  println(urv.name)
  println(estudiants.name)

  cat.printTree("")
  root.getUsers()

  val mailbox1 = mailSystem.createNewUser("user1", "user1", 1990);
  val mailbox2 = mailSystem.createNewUser("user2", "user2", 1999);

}
