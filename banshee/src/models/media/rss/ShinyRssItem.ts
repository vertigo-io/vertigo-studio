export class ShinyRssItem {
  title: string;
  link: string;
  description?: string;
  image?: string;
  pubDate?: string;
  author?: string;

  constructor(
    title: string,
    link: string,
    description?: string,
    image?: string,
    pubDate?: string,
    author?: string
  ) {
    this.title = title;
    this.link = link;
    this.description = description;
    this.image = image;
    this.pubDate = pubDate;
    this.author = author;
  }
}
