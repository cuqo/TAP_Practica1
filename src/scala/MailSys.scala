package scala

import part1.{MailSystem, MemoryMailStore}

object MailSys extends scala.App {
  val mailStore = new MemoryMailStore()
  val mailSystem = new MailSystem(mailStore)

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
  user1.mailbox = mailSystem.createNewUser("user1", "Joan", 2000)
  user2.mailbox = mailSystem.createNewUser("user2", "Joan", 2005)
  user3.mailbox = mailSystem.createNewUser("user3", "Maria", 1999)
  user4.mailbox = mailSystem.createNewUser("user4", "Laura", 1990)

  user1.mailbox.sendMail("user2", "hello", "Hello user2, this is user1!")
  user1.mailbox.sendMail("user1", "hello", "Hello user1, this is you!")
  user1.mailbox.sendMail("user4", "greetings", "Regards")
  user1.mailbox.sendMail("user3", "spam", "spam spam")
  user2.mailbox.sendMail("user1", "spam", "spam spam")

  println("All mail: " + root.getMail)

  val v = new FilterVisitor(m => !m.getBody.contains("spam"))
  root.accept(v)
  println("Filtered: " + v.messages)

  val c = new CounterVisitor()
  root.accept(c)
  println("Users: " + c.users + " Domains: " + c.domains)

  val f = new FoldFilterVisitor[Int](0, (acc, m) => acc + m.getBody.length, account => account.username.contains("user"))
  root.accept(f)
  println("Character count per user: " + f.users)

  user1.getMail.foreach(m => println(m))
  println("*******************************************************************************************************")

  val censoredList:List[String] = List("spam", "you")
  val list = user1.stackCensore(censoredList)(user1.getMail)
  list.foreach(m => println(m))
  println("*******************************************************************************************************")

  val list2 = user1.tailCensore(censoredList)(user1.getMail)
  list2.foreach(m => println(m))

  println("*******************************************************************************************************")
  val t = new TransformerVisitor(censoredList)
  root.accept(t)
  println("Censored list: " + t.messages)
  t.messages.foreach(m => println(m))


}
