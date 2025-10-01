class RssComponent extends Component {
    constructor({ title, items }) {
        super();
        this.title = title || 'RSS Feed';
        this.items = items || [];
        console.log('RssComponent initialized with items:', this.items);
    }
/*  Structure of an item
	title,
	link,
	description,
	image,
	pubDate,
	author*/
	
    toHtml() {
        if (this.items.length === 0) {
            console.warn('No items provided for RSS feed');
            return '<div>No items in the feed.</div>';
        }

        let feedHtml = '<div class="rss-feed">';
        this.items.forEach(item => {
            let parsedItem = item;
            if (typeof item === 'string') {
                console.log('Parsing string item:', item);
                const match = item.match(/\[(.*?)\]\((.*?)\)/);
                if (match && match.length === 3) {
                    parsedItem = {
                        title: match[1],
                        link: match[2],
                        description: '',
                        image: '',
                        pubDate: '',
                        author: ''
                    };
                } else {
                    parsedItem = {
                        title: item,
                        link: '',
                        description: '',
                        image: '',
                        pubDate: '',
                        author: ''
                    };
                }
            }

            const { title = 'Untitled', link = '', description = '', image = '', pubDate = '', author = '' } = parsedItem;

            // Valider et formater la date
            let formattedDate = '';
            if (pubDate) {
                try {
                    const date = new Date(pubDate);
                    if (!isNaN(date.getTime())) {
                        formattedDate = date.toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' });
                    } else {
                        console.warn('Invalid pubDate:', pubDate);
                    }
                } catch (e) {
                    console.error('Error parsing pubDate:', pubDate, e);
                }
            }

            // Valider l'image
            if (image) {
                console.log('Image URL:', image);
            }

            const summary = description.length > 150 
			? description.substring(0, 150) + '...' 
			: description;

            feedHtml += `
                <div class="rss-card">
                    ${image ? `<div class="rss-image"><img src="${image}" alt="${title}" loading="lazy" onerror="this.style.display='none';"></div>` : '<div class="rss-image-placeholder"></div>'}
                    <div class="rss-content">
                        <h3 class="rss-headline"><a href="${link}" target="_blank" rel="noopener noreferrer">${title}</a></h3>
                        <p class="rss-summary">${summary}</p>
                    </div>
                </div>
            `;
        });
        feedHtml += '</div>';
        return feedHtml;
    }
}