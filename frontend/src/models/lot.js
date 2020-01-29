export default class Lot {
  constructor (id, link, name, owner, price, bid = null, bidder = '', description = '', startDate = null, endDate = null, categories = [], images = []) {
    this.id = id
    this.link = link
    this.name = name
    this.owner = owner
    this.price = price
    this.description = description
    this.bidder = bidder
    this.startDate = new Date(startDate)
    this.endDate = new Date(endDate)
    this.categories = categories
    this.bid = bid
    this.images = images
  }
  setOwner (owner) {
    this.owner = owner
  }
  setBidder (bidder) {
    this.bidder = bidder
  }
}
