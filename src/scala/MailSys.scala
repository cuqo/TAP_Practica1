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

  cat.printTree("")

  user1.mailbox.sendMail("user2", "hello", "Hello user2, this is user1!")
  user1.mailbox.sendMail("user1", "hello", "Hello user1, this is you!")
  user1.mailbox.sendMail("user4", "greetings", "Regards")
  user1.mailbox.sendMail("user3", "spam", "spam spam")
  user2.mailbox.sendMail("user1", "spam", "spam spam")

  user1.getMail().foreach(msg => msg.toString)


}
