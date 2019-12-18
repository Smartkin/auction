export default class HeaderItem {
  constructor (nameId, name, link, isOnRight = false, onClickHandler = () => {}, isActive = false) {
    this.nameId = nameId
    this.name = name
    this.link = link
    this.isOnRight = isOnRight
    this.onClick = onClickHandler
    this.isActive = isActive
  }
}
