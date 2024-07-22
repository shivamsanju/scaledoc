import { Button, Dialog, Group, Modal, Stack, Text } from '@mantine/core'
import { IconAlertTriangle } from '@tabler/icons-react'
import { FC } from 'react'

type DeleteConfirmationModalProps = {
  open: boolean
  message: string
  onDelete: () => any
  onCancel: () => any
}
const DeleteConfirmationModal: FC<DeleteConfirmationModalProps> = ({
  open,
  message,
  onDelete,
  onCancel,
}) => {
  return (
    <Modal opened={open} onClose={onCancel} size="md" withCloseButton centered title="Warning" >
        <Group justify='center'>
          <Text size="sm" mb="xs" fw={500}>{message}</Text>
        </Group>
        
        <Button onClick={onDelete} size="xs" w="100%">
          Delete
        </Button>
    </Modal>
  )
}

export default DeleteConfirmationModal
