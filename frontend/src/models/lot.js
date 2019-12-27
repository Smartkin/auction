export default class Lot {
  constructor (id, link, name, owner, price, bid = null, bidder = null, description = '', categories = []) {
    this.id = id
    this.link = link
    this.name = name
    this.owner = owner
    this.price = price
    this.description = description
    this.bidder = bidder
    this.categories = categories
    this.bid = bid
  }
  setOwner (owner) {
    this.owner = owner
  }
  setBidder (bidder) {
    this.bidder = bidder
  }
}
