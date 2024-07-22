/* If you have some middlewares then replace 
StateCreator<MyState, [], [], MySlice> with 
StateCreator<MyState, Mutators, [], MySlice>.
For example, if you are using devtools then it will be 
StateCreator<MyState, [["zustand/devtools", never]], [], MySlice> 
*/
'use client'

import { AssetsSlice } from '@/types/assets'
import type { ChatsSlice, MessagesSlice } from '@/types/chats'
import type { ProjectsSlice } from '@/types/projects'
import { SocketSlice } from '@/types/socket'
import { UsersSlice } from '@/types/users'
import { create } from 'zustand'
import {
  createAssetsSlice,
  createChatsSlice,
  createMessagesSlice,
  createProjectsSlice,
  createSocketSlice,
  createUsersSlice,
} from './slices'

const useStore = create<
  ProjectsSlice &
    ChatsSlice &
    MessagesSlice &
    AssetsSlice &
    SocketSlice &
    UsersSlice
>()((...a) => ({
  ...createProjectsSlice(...a),
  ...createChatsSlice(...a),
  ...createMessagesSlice(...a),
  ...createAssetsSlice(...a),
  ...createSocketSlice(...a),
  ...createUsersSlice(...a),
}))

export default useStore
