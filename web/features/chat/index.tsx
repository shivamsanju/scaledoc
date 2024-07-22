import { getAssetsApi } from '@/apis/assets'
import { Divider } from '@mantine/core'
import { useEffect, useState } from 'react'
import useStore from '../../store'
import ChatWindow from './chat-window'
import styles from './chat.module.scss'
import ChatHistory from './history'

const ProjectDetailsScreen = () => {

  return (
    <div className={styles.content}>
      <div className={styles.menuContent}>
        <ChatHistory />
      </div>

      <Divider size="xs" orientation="vertical" />
      <div className={styles.tabContent}>
        <ChatWindow />
      </div>
    </div>
  )
}

export default ProjectDetailsScreen
