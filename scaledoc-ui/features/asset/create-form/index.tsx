import { createAssetApi } from '@/apis/assets'
import OverlayLoader from '@/components/Loader'
import useStore from '@/store'
import { Button, Grid, Group, Modal } from '@mantine/core'
import { isNotEmpty, useForm } from '@mantine/form'
import { showNotification } from '@mantine/notifications'
import { FC, useCallback, useState } from 'react'
import MetadataForm from './Metadata'

type CreateAssetFormProps = {
  projectId: string
  open: boolean
  onClose: () => void
  hideOneOnCreate: boolean
}

const CreateAssetForm: FC<CreateAssetFormProps> = ({
  projectId,
  open,
  onClose,
  hideOneOnCreate,
}) => {
  const [loading, setLoading] = useState(false)
  const addNewAsset = useStore((state) => state.addNewAsset)
  const assetTypes = useStore((state) => state.assetTypes)
  const users = useStore((state) => state.users)

  const form = useForm({
    initialValues: {
      name: '',
      file: null,
      description: '',
      tags: '',
      authors: ''
    },
    validate: {
      name: isNotEmpty('Enter a name'),
      file: isNotEmpty('Upload a file'),
    },
  })

  const handleSubmit = async (values: any) => {
    setLoading(true)
    createAssetApi(projectId, {
      name: values.name,
      description: values.description,
      tags: values.tags,
      metadata: JSON.stringify({authors: values.authors}),
    }, values.file)
      .then((asset) => {
        addNewAsset(asset, hideOneOnCreate)
        showNotification({
          message: 'Asset created successfully.',
          color: 'green',
        })
        handleReset()
      })
      .catch((e: Error) => {
        showNotification({ message: e.message.toString(), color: 'red' })
      })
      .finally(() => setLoading(false))
  }

  const handleReset = () => {
    form.reset()
    setLoading(false)
    onClose()
  }

  const getAssetTypeFromId = useCallback(
    (id: string) => {
      return assetTypes.find((e) => e.id === id)?.key
    },
    [assetTypes]
  )


  if (!assetTypes || !users) {
    return <OverlayLoader />
  }

  return (
    <Modal
      opened={open}
      size="xl"
      onClose={handleReset}
      title="Add Asset"
      closeOnClickOutside={false}
    >
      <form onSubmit={form.onSubmit((values) => handleSubmit(values))}>
        <Grid>
          <Grid.Col span={12}>
            <MetadataForm form={form} assetTypes={assetTypes} users={users} projectId={projectId}/>
          </Grid.Col>
        </Grid>
        <Group mt="lg" justify="flex-end">
          <Button type="submit" loading={loading} size="xs">
            Add
          </Button>
        </Group>
      </form>
    </Modal>
  )
}

export default CreateAssetForm
