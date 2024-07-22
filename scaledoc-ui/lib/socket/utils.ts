import { Server } from 'socket.io'

const cache = new Map<string, { value: string; expirationDate: number }>()

const SOCKET_KEY = 'SOCKET_KEY:'
const EXPIRATION_TIME_MS = 60 * 60 * 24 * 1000

const setWithExpiration = (key: string, value: string, expirationTime: number): void => {
  const expirationDate = Date.now() + expirationTime
  cache.set(key, { value, expirationDate })
}

const getWithExpiration = (key: string): string | null => {
  const entry = cache.get(key)
  if (!entry) return null
  if (Date.now() > entry.expirationDate) {
    cache.delete(key)
    return null
  }
  return entry.value
}

export const saveSocketClientId = async (userId: string, clientId: string): Promise<void> => {
  setWithExpiration(`${SOCKET_KEY}${userId}`, clientId, EXPIRATION_TIME_MS)
}

export const removeSocketClientId = async (userId: string): Promise<void> => {
  cache.delete(`${SOCKET_KEY}${userId}`)
}

export const getSocketClientId = async (userId: string): Promise<string | null> => {
  return getWithExpiration(`${SOCKET_KEY}${userId}`)
}

export const emitSocketEventToUser = async <T>(
  io: Server,
  to_user: string,
  event: string,
  payload: T
): Promise<void> => {
  const socketId = await getSocketClientId(to_user)
  if (socketId) io.to(socketId).emit(event, payload)
}
