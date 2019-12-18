export default class Lot {
  constructor (id, link, name, owner, price, bidder = null, description = '', categories = []) {
    this.id = id
    this.link = link
    this.name = name
    this.owner = owner
    this.price = price
    this.description = description
    this.bidder = bidder
    this.categories = categories
  }
}
