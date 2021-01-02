package scala

import part1.{Mailbox, Message}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._

trait AComponent {
  def name: String

  /*def accept: Unit*/
}

class Account(val username: String) extends AComponent {
  override def name: String = username

  /*override def accept: Unit = {}*/

  var mailbox: Mailbox = null

  def getMail: List[Message] = {
    mailbox.updateMail()
    mailbox.listMail().asScala.toList
  }

  def accept(visitorInterface: VisitorInterface): Unit = {
    visitorInterface.visit(this)
  }

  def stackCensore(censoredList: List[String])(messagesList: List[Message]): List[Message] = {
    val censoredMessageList: ListBuffer[Message] = new ListBuffer[Message]
    var messagesListRecursive:List[Message] = Nil

    if (messagesList.nonEmpty) {
      var censore: Boolean = false
      censoredList.foreach(str => {
        messagesList.head.getBody.split(" ").foreach(strBody => {
          if (strBody.contains(str)) {
            censore = true
          }
        })
      })
      if (censore) {
        val m: Message = new Message(messagesList.head.getSender, messagesList.head.getReceiver, messagesList.head.getSentTime, messagesList.head.getSubject, "CENSORED")
        censoredMessageList.addOne(m)
      } else censoredMessageList.addOne(messagesList.head)
      messagesListRecursive = messagesList.filterNot(m => m.equals(messagesList.head))
      censoredMessageList.appendAll(stackCensore(censoredList)(messagesListRecursive))
    }
    censoredMessageList.toList
  }

  def tailCensore(censoredList: List[String])(messagesList: List[Message]): List[Message] = {
    val censoredMessageList: ListBuffer[Message] = new ListBuffer[Message]

    @tailrec def curryingTailMessage(count: Int): Unit = {
      if (count < messagesList.size) {
        var censore: Boolean = false
        censoredList.foreach(str => {
          messagesList(count).getBody.split(" ").foreach(strBody => {
            if (strBody.contains(str)) {
              censore = true
            }
          })
        })
        if (censore) {
          val m: Message = new Message(messagesList(count).getSender, messagesList(count).getReceiver, messagesList(count).getSentTime, messagesList(count).getSubject, "CENSORED")
          censoredMessageList.addOne(m)
        } else censoredMessageList.addOne(messagesList(count))
        val c = count + 1
        curryingTailMessage(c)
      }
    }
    curryingTailMessage(0)
    censoredMessageList.toList
  }

}

class Domain(val domain: String) extends AComponent {
  /*override def accept: Unit = {}*/

  var childrenDomain: ListBuffer[AComponent] = new ListBuffer[AComponent]()

  def addChild(child: AComponent): Unit = {
    childrenDomain += child
  }

  def removeChild(child: AComponent): Unit = {
    childrenDomain -= child
  }

  override def name = domain

  def printTree: Unit = {
    printTreeNode("")
  }

  def printTreeNode(c:String): Unit = {
    println(c + "|" + name)
    childrenDomain.foreach(pos => pos match {
      case l: Domain =>
        val tab = c + "|\t"
        l.printTreeNode(tab)
      case l: Account =>
        println(c + "|\t|@" + l.name)
      case _ =>
    })
  }

  def getMail: ListBuffer[Message] = {
    val message: ListBuffer[Message] = new ListBuffer[Message]
    val users: ListBuffer[Account] = getUsers
    users.foreach(account => {
      message.addAll(account.getMail)
    })
    message
  }

  def getUsers: ListBuffer[Account] = {
    val users: ListBuffer[Account] = new ListBuffer[Account]
    childrenDomain.foreach(pos => pos match {
      case l: Domain =>
        users.addAll(l.getUsers)
      case l: Account =>
        users.addOne(l)
    })
    users
  }

  def accept(visitorInterface: VisitorInterface): Unit = {
    visitorInterface.visit(this)
  }
}
