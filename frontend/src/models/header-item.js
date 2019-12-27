export default class HeaderItem {
  constructor (nameId, name, link, onClickHandler = () => {}, isActive = false) {
    this.nameId = nameId
    this.name = name
    this.link = link
    this.onClick = onClickHandler
    this.isActive = isActive
  }
}
