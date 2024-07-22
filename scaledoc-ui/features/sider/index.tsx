import useStore from '@/store'
import {  Text } from '@mantine/core'
import {
  IconBulbFilled,
  IconSettings,
  IconFolderRoot,
} from '@tabler/icons-react'
import Link from 'next/link'
import { usePathname } from 'next/navigation'
import { useEffect, useMemo } from 'react'
import styles from './sider.module.scss'

const getParentPath = (path: string) =>
  path === '/' ? path : `/${path?.split('/')[1]}`

export default function Sider() {
  const pathname = usePathname()

  const items = useMemo(
    () => [
      { title: 'Ask', path: '/', icon: <IconBulbFilled /> },
      { title: 'Projects', path: '/projects', icon: <IconFolderRoot /> },
      { title: 'Settings', path: '/settings', icon: <IconSettings /> },
    ],
    []
  )

  return (
    <div className={styles.siderContainer}>
      <div className={styles.menuContainer}>
        {items.map((e) => (
          <div
            className={`${styles.navItem} ${
              getParentPath(pathname) == e.path ? styles.activeNavItem : ''
            }`}
          >
            <Link href={e.path} className={styles.navItemLink}>
              {e.icon}
              <Text
                style={{ fontSize: '0.57em' }}
                className={styles.navItemTitle}
              >
                {e.title}
              </Text>
            </Link>
          </div>
        ))}
      </div>
      <div className={styles.footer}>
        <Text size="sm" opacity={0.4}>
          V 0.1
        </Text>
      </div>
    </div>
  )
}
