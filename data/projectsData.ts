interface Project {
  title: string
  description: string
  href?: string
  imgSrc?: string
}

const projectsData: Project[] = [
  {
    title: 'HP',
    description: `A tutorial project to help Java Programmer to get the sense of measure the performance of HTTP server`,
    imgSrc: `${process.env.BASE_PATH || ''}/static/images/time-machine.jpg`,
    href: 'https://timzaak.github.io/hp/',
  },
  {
    title: 'ForNet',
    description: `It's a enterprise VPN project, based on Rust + Wireguard, support Linux, Windows, macOS OS platform.`,
    imgSrc: `${process.env.BASE_PATH || ''}/static/images/project/fornet.jpeg`,
    href: 'https://github.com/ForNetCode/fornet',
  },
  {
    title: 'Web Sugar',
    description: `It's a scala web api starter project, including some other projects, like React Admin Web, database code auto generate, wechat pay support.`,
    href: 'https://github.com/ForNetCode/web-sugar-startup',
  },
  {
    title: 'Desktop Message',
    description: `Mqtt + static file http server + service discovery for desktop app, to interact with devices.`,
    href: 'https://github.com/timzaak/desktop-message',
  },
  {
    title: 'Log Http',
    description: `log http/https request/response via MITM, for PC software developer who has none Chrome Console.`,
    href: 'https://github.com/timzaak/log-http-proxy',
  },
  {
    title: 'Table2Case',
    description: `convert database table to scala case class and scalaSQL ORM, support PG, MySQL, Sqlite.`,
    href: 'https://github.com/timzaak/table2case',
  },
  {
    title: 'DevOps Automation Utilities',
    description: `scripts about daily devops jobs, including bakeup, docker image transfer, encrypt decrypt file and so on`,
    href: 'https://github.com/timzaak/devops',
  },
]

export default projectsData
