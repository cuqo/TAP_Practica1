package scala

import part1.{MailSystem, MemoryMailStore, Message}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

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

  println("2- ")
  cat.printTree()
  user1.mailbox = mailSystem.createNewUser("user1", "Joan", 2000)
  user2.mailbox = mailSystem.createNewUser("user2", "Joan", 2005)
  user3.mailbox = mailSystem.createNewUser("user3", "Maria", 1999)
  user4.mailbox = mailSystem.createNewUser("user4", "Laura", 1990)

  user1.mailbox.sendMail("user2", "hello", "Hello user2, this is user1!")
  user1.mailbox.sendMail("user1", "hello", "Hello user1, this is you!")
  user1.mailbox.sendMail("user4", "greetings", "Regards")
  user1.mailbox.sendMail("user3", "spam", "spam spam")
  user2.mailbox.sendMail("user1", "spam", "spam spam")

  println("\n3- All mail: " + root.getMail)

  val v = new FilterVisitor(m => !m.getBody.contains("spam"))
  root.accept(v)
  println("\n5- Filtered: " + v.messages)

  val c = new CounterVisitor()
  root.accept(c)
  println("\n6- Users: " + c.users + " Domains: " + c.domains)

  val f = new FoldFilterVisitor[Int](0, (acc, m) => acc + m.getBody.length, account => account.username.contains("user"))
  root.accept(f)
  println("\n7- Character count per user: " + f.users)

  //TransformerVisitor with currying stack recursion
  val t = new TransformerVisitor(m => stackCensore(List("spam", "you"))(m))
  root.accept(t)
  println("\n9- (Stack) Censored domain: " + t.messages)

  //TransformerVisitor with currying tail recursion
  val z = new TransformerVisitor(m => tailCensore(List("spam", "you"))(m))
  user1.accept(z)
  println("\n9- (Tail) Censored user: " + z.messages)

  def stackCensore(censoredList: List[String])(messagesList: List[Message]): List[Message] = {
    val censoredMessageList: ListBuffer[Message] = new ListBuffer[Message]
    var messagesListRecursive: List[Message] = Nil

    if (messagesList.nonEmpty) {
      censoredMessageList.addOne(containsSpamWord(censoredList)(messagesList.head))
      messagesListRecursive = messagesList.filterNot(m => m.equals(messagesList.head))
      censoredMessageList.appendAll(stackCensore(censoredList)(messagesListRecursive))
    }
    censoredMessageList.toList
  }

  def tailCensore(censoredList: List[String])(messagesList: List[Message]): List[Message] = {
    val censoredMessageList: ListBuffer[Message] = new ListBuffer[Message]

    @tailrec def curryingTailMessage(count: Int): Unit = {
      if (count < messagesList.size) {
        censoredMessageList.addOne(containsSpamWord(censoredList)(messagesList(count)))
        val c = count + 1
        curryingTailMessage(c)
      }
    }

    curryingTailMessage(0)
    censoredMessageList.toList
  }

  def containsSpamWord(censoredList: List[String])(message: Message): Message = {
    var censore: Boolean = false
    censoredList.foreach(str => {
      message.getBody.split(" ").foreach(strBody => {
        if (strBody.contains(str)) {
          censore = true
        }
      })
    })
    if (censore) {
      val m: Message = new Message(message.getSender, message.getReceiver, message.getSentTime, message.getSubject, "CENSORED")
      m
    } else message
  }
}
