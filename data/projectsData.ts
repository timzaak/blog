interface Project {
  title: string
  description: string
  href?: string
  imgSrc?: string
}

const projectsData: Project[] = [
  {
    title: 'HP',
    description: `A tutorial project to help Java programmer to get the sense of measuring the performance of HTTP web server`,
    imgSrc: `/static/images/time-machine.jpg`,
    href: 'https://timzaak.github.io/hp',
  },
  {
    title: 'ForNet',
    description: `Enterprise VPN project, based on Rust + Wireguard, support Linux, Windows, macOS.`,
    imgSrc: `/static/images/project/fornet.jpeg`,
    href: 'https://github.com/ForNetCode/fornet',
  },
  {
    title: 'Web Sugar',
    description: `Scala web api starter project, including some other projects, like React Admin Web, database code auto generate, wechat pay support.`,
    href: 'https://github.com/ForNetCode/web-sugar-startup',
  },
  {
    title: 'Desktop Message',
    description: `MQTT + static file http server + service discovery for desktop app interacting with devices.`,
    href: 'https://github.com/timzaak/desktop-message',
  },
  {
    title: 'Log Http',
    description: `Log http/https request/response via MITM, for PC software developer who has none Chrome Console.`,
    href: 'https://github.com/timzaak/log-http-proxy',
  },
  {
    title: 'Notir',
    description: 'Simple pubsub via http and websocket, provide web console to handle the message.',
    href: 'https://github.com/timzaak/notir',
  },
  {
    title: 'Table2Case',
    description: `Convert database table to scala case class or scalaSQL ORM, support PG, MySQL, Sqlite.`,
    href: 'https://github.com/timzaak/table2case',
  },
  {
    title: 'DevOps Automation Utilities',
    description: `Scripts of daily devops jobs, including bake up, docker image transfer, encrypt decrypt file and so on`,
    href: 'https://github.com/timzaak/devops',
  },
]

export default projectsData
